package com.munch.suggest.data;

interface ItemClickListener {
    /**
     * Item clicked in recyclerView.
     *
     * @param adapterPosition position in recyclerView adapter
     */
    void onItemClicked(int adapterPosition);
}
