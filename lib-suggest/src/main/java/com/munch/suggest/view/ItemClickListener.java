package com.munch.suggest.view;

interface ItemClickListener {
    /**
     * Item clicked in recyclerView.
     *
     * @param adapterPosition position in recyclerView adapter
     */
    void onItemClicked(int adapterPosition);
}
