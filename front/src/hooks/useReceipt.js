import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import api from "../axios/axios";
import setupWebSocket from "../webosockets/websockets";
import { resetOrderState } from "../store/OrderSlice";

const useReceiptLogic = () => {
  const orderItems = useSelector((state) => state.order.items);
  const total = useSelector((state) => state.order.total);
  const processingTime = useSelector((state) => state.order.processingTime);
  const coffeeAmount = useSelector((state) => state.order.coffeeAmount);
  const [btnLoading, setBtnLoading] = useState(false);
  const [orderReady, setOrderReady] = useState(false);
  const [orderAccepted, setOrderAccepted] = useState(false);
  const [orderNo, setOrderNo] = useState();
  const dispatch = useDispatch();

  useEffect(() => {
    if (orderAccepted) {
      setupWebSocket(orderNo, handleWebSocketEvent);
    }
  }, [orderAccepted, orderNo]);

  const handleWebSocketEvent = (event) => {
    setOrderNo(JSON.parse(event.order).id)
    setOrderReady(true);
  };

  const makeOrder = async () => {
    setBtnLoading(true);
    const data = {
      total,
      processingTime,
      coffeeAmount,
      items: orderItems.map((item) => item.id),
    };

    try {
      const response = await api.post("/orders", data);
      if (response.data.type === "success") {
        setOrderAccepted(true);
        setOrderNo(response.data.data.id);
        dispatch(resetOrderState());
      }
    } catch (error) {
      console.error(error);
    }

    setBtnLoading(false);
  };

  return {
    orderItems,
    total,
    btnLoading,
    orderReady,
    orderAccepted,
    orderNo,
    makeOrder,
    setOrderReady,
    setOrderAccepted
  };
};

export default useReceiptLogic;