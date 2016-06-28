package com.mbopartners.mbomobile.ui.activity.choose_expense_type;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbopartners.mbomobile.rest.model.response.ExpenseType;
import com.mbopartners.mbomobile.ui.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ChooseExpenseTypeActivityFragment extends Fragment {

    private static Map<String, IdHolder> expensePictureLinker;
    static {
        expensePictureLinker = fillPictureLinker();
    }

    private OnFragmentInteractionListener mListener;
    private RecyclerView expenseTypesRecyclerView;
    private ExpenseTypeRecyclerViewAdapter adapter;

    public static ChooseExpenseTypeActivityFragment newInstance() {
        ChooseExpenseTypeActivityFragment fragment = new ChooseExpenseTypeActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ChooseExpenseTypeActivityFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + ChooseExpenseTypeActivityFragment.OnFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentRootView = inflater.inflate(R.layout.fragment_choose_expense_type, container, false);
        expenseTypesRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.recyclerView);

        expenseTypesRecyclerView.setHasFixedSize(true);
        expenseTypesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new ExpenseTypeRecyclerViewAdapter(getExpenseTypesList(),getActivity());
        adapter.SetOnItemClickListener(new ExpenseTypeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String expenseTypeId,int position) {
                onExpenseTypeSelected(expenseTypeId,position);
            }
        });

        expenseTypesRecyclerView.setAdapter(adapter);
        expenseTypesRecyclerView.setScrollContainer(true);
        return fragmentRootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private List<ExpenseType> getExpenseTypesList () {
        if (mListener != null) {
            return mListener.getExpenseTypesList();
        }
        return new ArrayList<>();
    }

    public void onExpenseTypeSelected(String expenseTypeId, int position) {
        if (mListener != null) {
            mListener.onExpenseTypeSelected(expenseTypeId, position);
        }
    }

    public void onExpenseTypesReceived() {
        if (mListener != null) {
            adapter.updateDataSource(mListener.getExpenseTypesList());
        }
    }

    public interface OnFragmentInteractionListener {
        List<ExpenseType> getExpenseTypesList();
        void onExpenseTypeSelected(String expenseTypeId, int position);
    }

    public static int getExpenseImageIdOne(String expenseTypeId) {
        IdHolder holder = expensePictureLinker.get(expenseTypeId);
        int imageId = R.drawable.types_of_expence_unknown;
        if (holder != null) {
            imageId = holder.pictureOne;
        }
        return imageId;
    }

    public static int getExpenseImageIdTwo(String expenseTypeId) {
        IdHolder holder = expensePictureLinker.get(expenseTypeId);
        int imageId = R.drawable.log_expense_unknown;
        if (holder != null) {
            imageId = holder.pictureTwo;
        }
        return imageId;
    }

    public static Map<String, IdHolder> fillPictureLinker() {
        Map<String, IdHolder> linker = new Hashtable<>(20);
        linker.put("2592", new IdHolder(R.drawable.types_of_expence_meails_travel, R.drawable.log_expense_meals_travel));
        linker.put("1390", new IdHolder(R.drawable.types_of_expence_mileage, R.drawable.log_expense_mileage));
        linker.put("1404", new IdHolder(R.drawable.types_of_expence_bus, R.drawable.log_expense_bus));
        linker.put("286", new IdHolder(R.drawable.types_of_expence_meals_entertainment, R.drawable.log_expense_meals_entertainment));
        linker.put("1403", new IdHolder(R.drawable.types_of_expence_airfare, R.drawable.log_expense_aifare));
        linker.put("300", new IdHolder(R.drawable.types_of_expence_miscellaneous, R.drawable.log_expense_miscellaneous));
        linker.put("2357", new IdHolder(R.drawable.types_of_expence_airline_baggage, R.drawable.log_expense_airline_baggage));
        linker.put("1420", new IdHolder(R.drawable.types_of_expence_fuel, R.drawable.log_expense_fuel));
        linker.put("1419", new IdHolder(R.drawable.types_of_expence_parking, R.drawable.log_expense_parking));
        linker.put("1408", new IdHolder(R.drawable.types_of_expence_rail, R.drawable.log_expense_rail));
        linker.put("1836", new IdHolder(R.drawable.types_of_expence_tip, R.drawable.log_expense_tip));
        linker.put("1407", new IdHolder(R.drawable.types_of_expence_toll, R.drawable.log_expense_toll));
        linker.put("1406", new IdHolder(R.drawable.types_of_expence_taxi, R.drawable.log_expense_taxi));
        linker.put("1405", new IdHolder(R.drawable.types_of_expence_car_rental, R.drawable.log_expense_car_rental));
        linker.put("1396", new IdHolder(R.drawable.types_of_expence_hotel, R.drawable.log_expense_hotel));
        linker.put("298", new IdHolder(R.drawable.types_of_expence_perdiem, R.drawable.log_expense_perdiem));
        return linker;
    }

    private static class IdHolder {
        public int pictureOne;
        public int pictureTwo;

        public IdHolder(int pictureOne, int pictureTwo) {
            this.pictureOne = pictureOne;
            this.pictureTwo = pictureTwo;
        }
    }



}
