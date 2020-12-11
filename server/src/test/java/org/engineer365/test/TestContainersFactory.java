/*
 * MIT License
 *
 * Copyright (c) 2020 engineer365.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.engineer365.test;

import java.io.File;

import org.engineer365.common.misc.ClassHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PruneCmd;
import com.github.dockerjava.api.model.PruneResponse;
import com.github.dockerjava.api.model.PruneType;

import lombok.extern.slf4j.Slf4j;

/**
 * 集成测试：testcontainers + docker-compose
 *
 *
 * TODO：
 * 1) 考虑去掉端口号等被hardcode的常量
 * 2) 考虑https://github.com/Playtika/testcontainers-spring-boot
 */
@lombok.Getter
@lombok.Setter
@Slf4j
public class TestContainersFactory {

  public static final TestContainersFactory DEFAULT = new TestContainersFactory();

  int serverPort = 8080;
  int serverManagePort = 8081;
  String serverHealthEndpoint = "/manage/health";
  final Logger serverLogger = LoggerFactory.getLogger(ClassHelper.parseNameSuffix(getClass()) + ".server");

  int mySQLPort = 3306;
  final Logger mysqlLogger = LoggerFactory.getLogger(ClassHelper.parseNameSuffix(getClass()) + ".mysql");

  static {
    pruneDocker();
  }

  /**
   *
   * 清理docker。
   *
   * 如果 <Ctrl>+C 强行中止正在执行的测试,
   * 测试时所创建的docker network就不会被自动释放，但是docker network默认只能创建30个，
   * 所以test执行较多次数以后，docker network会耗尽。
   * 类似的还有容器和volume等，所以，我们需要随时清理
   */
  public synchronized static void pruneDocker() {
    DockerClient client = DockerClientFactory.instance().client();
    try (PruneCmd cmd = client.pruneCmd(PruneType.NETWORKS);) {
      PruneResponse resp = cmd.exec();
      log.info("prune docker network prune: " + resp);
    }

    try (PruneCmd cmd = client.pruneCmd(PruneType.BUILD);) {
      PruneResponse resp = cmd.exec();
      log.info("prune docker build: " + resp);
    }

    try (PruneCmd cmd = client.pruneCmd(PruneType.CONTAINERS);) {
      PruneResponse resp = cmd.exec();
      log.info("prune docker container: " + resp);
    }

    try (PruneCmd cmd = client.pruneCmd(PruneType.VOLUMES);) {
      PruneResponse resp = cmd.exec();
      log.info("prune docker volume: " + resp);
    }
  }

  public DockerComposeContainer<?> build() {
    return build(new File("dev/docker-compose.integration-test.yml"));
  }

  public DockerComposeContainer<?> build(File dockerComposeFile) {
    DockerComposeContainer<?> r = new DockerComposeContainer<>(dockerComposeFile);

    r.withLocalCompose(true);

    r.withExposedService("mysql", getMySQLPort(), Wait.forHealthcheck())
      .withLogConsumer("mysql", new Slf4jLogConsumer(getMysqlLogger()));

    r.withExposedService("server", getServerPort())
      .withExposedService("server", getServerManagePort(), Wait.forHttp(getServerHealthEndpoint()))
      .withLogConsumer("server", new Slf4jLogConsumer(getServerLogger()));

    return r;
  }

}
