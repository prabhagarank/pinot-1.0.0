/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pinot.connector.spark.v3.datasource

import org.apache.pinot.connector.spark.common.PinotDataSourceReadOptions
import org.apache.pinot.connector.spark.common.query.ScanQuery
import org.apache.pinot.spi.config.table.TableType
import org.apache.spark.sql.connector.read.PartitionReaderFactory
import org.apache.spark.sql.types.{IntegerType, StructField, StructType}

class PinotScanTest extends BaseTest {
  test("Scan should build and return a partition reader") {
    val optMap = new java.util.HashMap[String, String]()
    optMap.put("table", "myTable")
    optMap.put("tableType", "REALTIME")
    optMap.put("broker", "localhost:7177")
    val readOptions = PinotDataSourceReadOptions.from(optMap)
    val schema = StructType(
      Seq(StructField("myCol", IntegerType))
    )
    val scanQuery = ScanQuery(
      "myTable",
      Some(TableType.OFFLINE),
      "select * from myTable",
      "")

    val scan = new PinotScan(scanQuery, schema, readOptions)
    val readerFactory = scan.createReaderFactory()



    // assert PinotScan creates a PartitionReaderFactory
    readerFactory shouldBe a [PartitionReaderFactory]
  }
}
