import { useState, useEffect } from "react";
import api from "../axios/axios";

function useGetCoffees() {
  const [coffees, setCoffees] = useState();
  const [loader, setLoader] = useState(true);

  useEffect(() => {
    fetchCoffees();
  }, []);

  const fetchCoffees = async () => {
    setLoader(true);
    await api
      .get("/coffees")
      .then((response) => {
        setCoffees(response.data.data);
      })
      .catch((error) => {
        console.log(error);
      });
    setLoader(false);
  };

  return {
    coffees,
    loader,
  };
}

export default useGetCoffees;
