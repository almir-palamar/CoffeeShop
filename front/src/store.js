import { configureStore } from '@reduxjs/toolkit'
import orderReducer from './store/OrderSlice'

export default configureStore({
  reducer: {
    order: orderReducer
  },
})