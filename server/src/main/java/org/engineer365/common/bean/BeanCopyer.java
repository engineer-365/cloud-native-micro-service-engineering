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
package org.engineer365.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.cglib.beans.BeanCopier;
// import org.springframework.cglib.core.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 *
 */
@lombok.Getter
public class BeanCopyer<SOURCE,TARGET> {

    final BeanCopier copier;

    final Supplier<TARGET> targetSupplier;

    Function<Integer, TARGET[]> targetArraySupplier;

    // private final Converter converter;

    public BeanCopyer( Class<SOURCE> sourceClass,
                      Class<TARGET> targetClass,
                      Supplier<TARGET> targetSupplier,
                      Function<Integer, TARGET[]> targetArraySupplier) {
        this.targetSupplier = targetSupplier;
        this.targetArraySupplier = targetArraySupplier;

        this.copier = BeanCopier.create(sourceClass, targetClass, false);
    }

    public TARGET newTarget() {
        return getTargetSupplier().get();
    }

    public TARGET[] newTargetArray(int length) {
        return getTargetArraySupplier().apply(length);
    }

    public CompletableFuture<TARGET> copy(CompletableFuture<SOURCE> sourceFuture) {
        return sourceFuture.thenApply(this::copy);
    }

    public CompletableFuture<TARGET[]> copyToArray(CompletableFuture<List<SOURCE>> sourcesFuture) {
        return sourcesFuture.thenApply(this::copyToArray);
    }

    public Page<TARGET> copy(Page<SOURCE> sourcePage) {
        var targetContent = copyToList(sourcePage.getContent());
        return new PageImpl<TARGET>(targetContent, sourcePage.getPageable(), sourcePage.getTotalElements());
    }

    public TARGET copy(SOURCE source) {
        if (source == null) {
            return null;
        }
        var r = newTarget();
        render(source, r);
        return r;
    }

    public void render(SOURCE source, TARGET target) {
        if (source != null && target != null) {
            doRender(source, target);
        }
    }

    protected void doRender(SOURCE source, TARGET target) {
        getCopier().copy(source, target, null);
    }

    public TARGET[] copyToArray(List<SOURCE> sources) {
        if (sources == null) {
            return null;
        }
        return copyToArray(sources, sources.size());
    }

    public TARGET[] copyToArray(Iterable<SOURCE> sources, int size) {
        if (sources == null) {
            return null;
        }

        var r = new ArrayList<TARGET>(size);
        for (var src: sources) {
            r.add(copy(src));
        }

        return r.toArray(getTargetArraySupplier().apply(r.size()));
    }

    public List<TARGET> copyToList(List<SOURCE> sources) {
        if (sources == null) {
            return null;
        }
        return copyToList(sources, sources.size());
    }

    public List<TARGET> copyToList(Iterable<SOURCE> sources, int size) {
        if (sources == null) {
            return null;
        }

        var r = new ArrayList<TARGET>();
        for (var src: sources) {
            r.add(copy(src));
        }

        return r;
    }

}
