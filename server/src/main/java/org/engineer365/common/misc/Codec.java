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

import org.apache.commons.codec.binary.Base64;

/**
 * 基本的编解码工具
 */
public class Codec {

  /**
   * 字节数组编码成Base64（可以用于URL）
   */
  public static String bytesToBase64(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    return Base64.encodeBase64URLSafeString(bytes);
  }

  /**
   * Base64转字节数组
   */
  public static byte[] base64ToBytes(String base64ed) {
    if (base64ed == null) {
      return null;
    }
    return Base64.decodeBase64(base64ed + "==");
  }

  /**
   * long转换成字节数组
   */
  static void longTobytes(long value, byte[] bytes, int offset) {
    for (int i = 7; i > -1; i--) {
      bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
    }
  }

  /**
   * 字节数组转换成long
   */
  static long bytesTolong(byte[] bytes, int offset) {
    long value = 0;
    for (int i = 7; i > -1; i--) {
      value |= (((long) bytes[offset++]) & 0xFF) << 8 * i;
    }
    return value;
  }

}
