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
package org.engineer365.common.dao.jpa;

import org.hibernate.dialect.MySQL8Dialect;
import org.engineer365.common.error.InternalServerError;
import org.hibernate.dialect.Database;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;


@lombok.extern.slf4j.Slf4j
public class DialectResolverEx implements DialectResolver {

  /**
   *
   */
  private static final long serialVersionUID = 6786967837824426241L;

  @Override
  public Dialect resolveDialect(DialectResolutionInfo dialectResolutionInfo) {

    String dbName = dialectResolutionInfo.getDatabaseName();
    int verMajor = dialectResolutionInfo.getDatabaseMajorVersion();
    int verMinor = dialectResolutionInfo.getDatabaseMinorVersion();

    log.info("databaseName={}, majorVersion={}, minorVersion={}", dbName, verMajor, verMinor);

    if ("MySQL".equals(dbName) ) {
      if (verMajor == 8) {
        return new MySQL8Dialect();
      }
      throw new InternalServerError(InternalServerError.Code.OTHER, 
                    "Unsupported MySQL version: " + verMajor + "." + verMinor);
    }

    for (Database db : Database.values()) {
      Dialect dialect = db.resolveDialect(dialectResolutionInfo);
      if (dialect != null) {
        return dialect;
      }
    }

    return null;
  }

}
