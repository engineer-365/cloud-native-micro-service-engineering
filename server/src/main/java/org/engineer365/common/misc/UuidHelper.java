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
package org.engineer365.common.misc;

import java.util.UUID;

/**
 * UUID工具
 */
public class UuidHelper {

  /**
   * 生成短UUID字符串（22位）
   * @return
   */
  public static String shortUuid() {
    var uuid = UUID.randomUUID();
    return compress(uuid);
  }

  /**
   * 把UUID对象压缩成短UUID字符串
   */
  public static String compress(UUID uuid) {
    byte[] byUuid = new byte[16];
    long least = uuid.getLeastSignificantBits();
    long most = uuid.getMostSignificantBits();
    Codec.longTobytes(most, byUuid, 0);
    Codec.longTobytes(least, byUuid, 8);
    return Codec.bytesToBase64(byUuid);
  }

  /**
   * 把短UUID字符串解压缩UUID对象
   */
  public static UUID uncompress(String compressedUuid) {
    if (compressedUuid.length() != 22) {
      throw new IllegalArgumentException("Invalid uuid!");
    }
    byte[] byUuid = Codec.base64ToBytes(compressedUuid);
    long most = Codec.bytesTolong(byUuid, 0);
    long least = Codec.bytesTolong(byUuid, 8);
    return new UUID(most, least);
  }

}
