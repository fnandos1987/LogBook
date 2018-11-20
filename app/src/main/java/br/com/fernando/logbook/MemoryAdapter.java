package br.com.fernando.logbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.fernando.model.Memory;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder> {

    public final onMemoryClickListener listener;
    private List<Memory> memoryList = new ArrayList<>();

    public MemoryAdapter(onMemoryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(
                android.R.layout.simple_list_item_2,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Memory memory = memoryList.get(position);

        holder.title.setText(memoryList.get(position).getTitle());

        holder.itemView.setOnClickListener(view -> listener.onClick(memory));
    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    public void setup(List<Memory> tasks) {
        this.memoryList.clear();
        this.memoryList.addAll(tasks);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
            date = itemView.findViewById(android.R.id.text2);
        }

    }

    interface onMemoryClickListener {
        void onClick(Memory task);
    }
}
