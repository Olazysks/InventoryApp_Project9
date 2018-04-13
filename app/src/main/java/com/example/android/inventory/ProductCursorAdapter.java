package com.example.android.inventory;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventory.data.InventoryContract;

/**
 * {@link ProductCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of product data as its data source. This adapter knows
 * how to create list items for each row of product data in the {@link Cursor}.
 */
public class ProductCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView authorTextView = (TextView) view.findViewById(R.id.author);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_NAME);
        int authorColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_AUTHOR);
        int priceColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRICE);

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        String productAuthor = cursor.getString(authorColumnIndex);
        String price = cursor.getString(priceColumnIndex);

        // If the product price is empty string or null, then use some default text
        // that says "Unknown price", so the TextView isn't blank.
        if (TextUtils.isEmpty(price)) {
            price = context.getString(R.string.unknown_price);
        }

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        authorTextView.setText(productAuthor);
        priceTextView.setText(context.getString(R.string.price_with_unit, price));
    }
}