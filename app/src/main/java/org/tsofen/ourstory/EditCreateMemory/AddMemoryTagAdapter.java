package org.tsofen.ourstory.EditCreateMemory;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourstory.R;

import org.tsofen.ourstory.model.Tag;

import java.util.List;

public class AddMemoryTagAdapter extends RecyclerView.Adapter<AddMemoryTagAdapter.ViewHolder> {

    List<Tag> tags;
    Context ctx;
    RecyclerView rv;

    public AddMemoryTagAdapter(List<Tag> tags, RecyclerView rv) {
        super();
        this.tags = tags;
        this.rv = rv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.addmemory_tags_rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView closeButton = holder.itemView.findViewById(R.id.imageView_tags_rv);
        AutoCompleteTextView editText = holder.itemView.findViewById(R.id.cememory_rv_text);
        if (position == tags.size()) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT); // Enables input
            closeButton.setVisibility(View.GONE);
            editText.setOnEditorActionListener((textView, i, keyEvent) -> {
                // This actually adds the tag whenever the user presses the enter.

                if (i == EditorInfo.IME_ACTION_DONE && keyEvent == null) {
                    Tag t = new Tag();
                    t.setLabel(editText.getText().toString());
                    tags.add(t);
                    notifyItemInserted(tags.size() - 1);
                    editText.setText("");
                    rv.scrollToPosition(tags.size());
                    editText.dismissDropDown();
                }
                return true;
            });
            editText.setOnClickListener(view -> {
                editText.showDropDown();
            });
            // TODO: Replace this with using a suggestion API
            final String[] suggestions = new String[]{"sunset", "beach", "water", "sky", "beer"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx,
                    R.layout.tags_dropdown_item, suggestions);
            editText.setAdapter(adapter);
            editText.showDropDown();
        } else {
            editText.setText(tags.get(position).getLabel());
            editText.setInputType(InputType.TYPE_NULL);
            closeButton.setVisibility(View.VISIBLE);
            closeButton.setOnClickListener(view -> {
                tags.remove(position);
                notifyItemRemoved(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return tags.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}