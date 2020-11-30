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
package org.engineer365.common.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public enum Role {

    Root,
    Admin,
    User;

    public static final Set<Role> ALL;
    static {
        var t = new HashSet<Role>();
        for (var role: values()) {
            t.add(role);
        }
        ALL = Collections.unmodifiableSet(t);
    }

    public static Set<Role> parse(String names) {
        if (names == null) {
            return null;
        }
        if (names.isBlank()) {
            return new HashSet<Role>();
        }

        var arr = names.split(",");
        var r = new HashSet<Role>();
        for (var name: arr) {
            r.add(Role.valueOf(name));
        }
        return r;
    }

    public static String format(Collection<Role> roles) {
        if (roles == null) {
            return null;
        }
        if (roles.isEmpty()) {
            return "";
        }

        var t = new StringBuilder();
        boolean first = true;
        for (Role role: roles) {
            if (first) {
                first = false;
            } else {
                t.append(',');
            }
            t.append(role.name());
        }

        return t.toString();
    }

}
