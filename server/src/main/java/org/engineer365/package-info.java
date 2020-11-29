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
package org.engineer365;

/**
 * 根package
 *
 * 2020-11-28:
 * 暂时只是一个单体应用，即所有代码部署在一起。
 *
 * package的结构遵循分层和模块化原则，分3个sub package，分别表示三个层次：
 * 1. common
 *    基础代码和工具代码，和服务化无关，也和应用无关
 *
 * 2. platform
 *    这下面的每一个sub package都是一个公共模块，譬如账号模块、消息模块。每个模块都是后续划分成平台级微服务的候选。
 *    “公共模块”意味着这些模块可以被不同的业务应用通用，所以，是否足够通用是放入这个package的判断标准。
 *    用比较流行的大词来说，这里是“某种”“中台”系统。
 *
 * 3. fleashop
 *    这里是直接实现二手书店电商的业务模块，这下面的每一个sub package都是一个业务模块，是后续划分成业务级微服务的候选。
 *
 *
 */