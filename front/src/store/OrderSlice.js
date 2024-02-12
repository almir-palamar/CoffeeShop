import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  items: [],
  total: parseFloat(0.00).toFixed(2),
  processingTime: 0,
  coffeeAmount: 0,
};

export const orderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    addItem: (state, action) => {
      const newItem = action.payload;

      const existingItem = state.items.find((item) => item.id === newItem.id);

      if (existingItem) {
        existingItem.count += 1;
      } else {
        state.items.push({ ...newItem, count: 1 });
      }
      state.total = (
        parseFloat(state.total) + parseFloat(action.payload.price)
      ).toFixed(2);
      state.processingTime += action.payload.brew_time;
      state.coffeeAmount += action.payload.coffee_amount;
    },
    removeItem: (state, action) => {
      state.items = state.items.filter((item) => item.id !== action.payload.id);
      state.total = (
        parseFloat(state.total) -
        action.payload.count * parseFloat(action.payload.price)
      ).toFixed(2);
      state.processingTime =
        state.processingTime - action.payload.count * action.payload.brew_time;
      state.coffeeAmount =
        state.coffeeAmount -
        action.payload.count * action.payload.coffee_amount;
    },
    resetOrderState: (state) => {
      return initialState
    },
  },
});

export const { addItem, removeItem, resetOrderState } = orderSlice.actions;

export default orderSlice.reducer;
