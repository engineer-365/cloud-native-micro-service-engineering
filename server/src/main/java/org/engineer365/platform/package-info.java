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
package org.engineer365.platform;

/**
 * 公共模块的根package。
 *
 * 2020-11-28:
 * 每一个模块都是后续一个微服务的候选，package的组织上约定如下：
 *
 *  ├── sdk：SDK里定义了该模块的API接口，相关的辅助类，和客户端实现类。
 *  |        每个模块都有自己的sdk包，sdk包后续会改成独立于模块的服务器端包单独发布。
 *  |   | API接口：通常和Controller类一一对应。
 *  │   ├── bean: entity bean的视图对象，VO。
 *  │   ├── client: API接口的客户端实现类，譬如一个AccountAPI接口可以有RESTful客户端实现类，还可以有gRPC或Dubbo客户端实现类。
 *  │   ├── enums：SDK里用到的枚举类。
 *  │   ├── rnr：rnr是Request And Response的三个单词的首字母，是API接口的输入输出参数里除了bean以外的其他数据对象，即DTO。
 *  └── app：服务器端实现
 *      ├── config：配置类
 *      ├── controller：接口层。接口层调用业务逻辑层里的API接口实现类。
 *      ├── dao：数据访问层。由业务逻辑层调用。数据访问层通常是读写数据库，但更宽泛的意义上，也是访问其他外部资源的入口。
 *      │   ├── ex：需要自定义实现的DAO接口（相对于用spring data JPA运行时自动生成实现的DAO接口）。
 *      │   ├── jpa：需要自定义实现的DAO接口的实现类，用QueryDSL实现。
 *      ├── entity：实体类。
 *      └── service：业务逻辑层。有两层业务逻辑类，第一层是API接口的直接实现类，controller直接调用这些业务逻辑类；第一层几个类。
 *                   有可以共用的实现的话，可以抽象成第二层业务逻辑类由。API接口实现类调用。
 *
 */