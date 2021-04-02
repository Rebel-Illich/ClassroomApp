package com.hunter.myclassroommap.searchBy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;

import java.util.ArrayList;
import java.util.List;

public class SearchByClassRoomsRecyclerAdapter extends RecyclerView.Adapter<SearchByClassRoomsRecyclerAdapter.ViewHolder> implements Filterable {

    private final List<ClassRoom> classRoomList;
    private final List<ClassRoom> filterClassRoomList;
    private SearchByContract.Presenter presenter;

    public SearchByClassRoomsRecyclerAdapter(List<ClassRoom> classRoomList) {
        this.classRoomList = classRoomList;
        this.filterClassRoomList = new ArrayList<>(classRoomList);
    }

    public void registerSearchByListener(SearchByContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public SearchByClassRoomsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_by_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassRoom classRoom = classRoomList.get(position);

        holder.searchResult.setText(classRoom.getClassroomName());
    }

    @Override
    public int getItemCount() {
        return classRoomList.size();
    }

    @Override
    public Filter getFilter() {
        return classRoomFilter;
    }

    private final Filter classRoomFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ClassRoom> resultList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                resultList.addAll(filterClassRoomList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ClassRoom classRoom : filterClassRoomList) {
                    if (classRoom.getClassroomName().toLowerCase().contains(filterPattern)) {
                        resultList.add(classRoom);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = resultList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            classRoomList.clear();
            classRoomList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView searchResult;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchResult = itemView.findViewById(R.id.search_result_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onItemClassRoomClicked(filterClassRoomList.get(getAdapterPosition()));
                }
            });
        }
    }
}
