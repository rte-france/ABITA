/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/*
 * Copyright 2006-2009 Enumlab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.web.common.data;

/**
 *
 * Date: 9 dec. 2005 Time: 22:03:10
 */
import java.io.Serializable;
import java.util.List;

/**
 * A simple class that represents a "page" of data out of a longer set, ie a list of objects together with news to
 * indicate the starting row and the full size of the dataset. EJBs can return instances of this type when returning
 * subsets of available data.
 */
public class DataPage implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 408671186668773208L;

    /** data set size */
    private int datasetSize;

    /** start row */
    private int startRow;

    /** data list */
    private List<?> data;

    /**
     * Create an object representing a sublist of a dataset.
     * @param datasetSize is the total number of matching rows available.
     * @param startRow is the index within the complete dataset of the first element in the data list.
     * @param data is a list of consecutive objects from the dataset.
     */
    public DataPage(int datasetSize, int startRow, List<?> data) {
        this.datasetSize = datasetSize;
        this.startRow = startRow;
        this.data = data;
    }

    /**
     * Return the number of items in the full dataset.
     * @return data set size
     */
    public int getDatasetSize() {
        return datasetSize;
    }

    /**
     * Return the offset within the full dataset of the first element in the list held by this object.
     * @return start row
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * Return the list of objects held by this object, which is a continuous subset of the full dataset.
     * @return data list
     */
    public List<?> getData() {
        return data;
    }
}
