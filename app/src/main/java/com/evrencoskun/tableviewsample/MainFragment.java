/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.evrencoskun.tableviewsample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableviewsample.tableview.TableViewAdapter;
import com.evrencoskun.tableviewsample.tableview.TableViewListener;
import com.evrencoskun.tableviewsample.tableview.model.Cell;
import com.evrencoskun.tableviewsample.tableview.model.ColumnHeader;
import com.evrencoskun.tableviewsample.tableview.model.RowHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public static final int COLUMN_SIZE = 100;
    public static final int ROW_SIZE = 100;

    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    private AbstractTableAdapter mTableViewAdapter;
    private TableView mTableView;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RelativeLayout fragment_container = view.findViewById(R.id.fragment_container);

        // Create Table view
        mTableView = createTableView();
        fragment_container.addView(mTableView);

        loadData();
        return view;
    }

    private TableView createTableView() {
        TableView tableView = new TableView(getContext());

        // Set adapter
        mTableViewAdapter = new TableViewAdapter(getContext());
        tableView.setAdapter(mTableViewAdapter);

        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);

        // Set TableView listener
        tableView.setTableViewListener(new TableViewListener(tableView));
        return tableView;
    }


    private void initData() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadData() {
        List<RowHeader> rowHeaders = getRowHeaderList();
        List<List<Cell>> cellList = getCellListForSortingTest(); // getCellList();
        List<ColumnHeader> columnHeaders = getColumnHeaderList(); //getRandomColumnHeaderList(); //

        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }

        // Load all data
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }

    private List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), "row " + i);
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    public static List<RowHeader> getRowHeaderList(int startIndex) {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), "row " + (startIndex + i));
            list.add(header);
        }

        return list;
    }


    private List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title = "column " + i;
            if (i % 6 == 2) {
                title = "large column " + i;
            }
            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<ColumnHeader> getRandomColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title = "column " + i;
            int nRandom = new Random().nextInt();
            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                title = "large column " + i;
            }

            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }

    private List<List<Cell>> getCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + i;
                if (j % 4 == 0 && i % 5 == 0) {
                    text = "large cell " + j + " " + i + ".";
                }
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                Object text = "cell " + j + " " + i;

                if (j == 0) {
                    text = i;
                } else if (j == 1) {
                    int random = new Random().nextInt();
                    text = random;
                }

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<List<Cell>> getRandomCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + i;
                int random = new Random().nextInt();
                if (random % 2 == 0 || random % 5 == 0 || random == j) {
                    text = "large cell  " + j + " " + i + getRandomString() + ".";
                }

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    public static List<List<Cell>> getRandomCellList(int startIndex) {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + (i + startIndex);
                int random = new Random().nextInt();
                if (random % 2 == 0 || random % 5 == 0 || random == j) {
                    text = "large cell  " + j + " " + (i + startIndex) + getRandomString() + ".";
                }

                String id = j + "-" + (i + startIndex);

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
        }

        return list;
    }


    private static String getRandomString() {
        Random r = new Random();
        String str = " a ";
        for (int i = 0; i < r.nextInt(); i++) {
            str = str + " a ";
        }

        return str;
    }
}
