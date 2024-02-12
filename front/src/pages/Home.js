import { Grid, CircularProgress } from "@mui/material";
import Coffee from "../components/Coffee";
import Receipt from "../components/Receipt/Receipt";
import api from "../axios/axios";
import { useEffect, useState } from "react";

function Home() {
  const [coffees, setCoffees] = useState();
  const [loader, setLoader] = useState(true);

  useEffect(() => {
    fetchCoffees();
  }, []);

  const fetchCoffees = async () => {
    setLoader(true)
    await api
      .get("/coffees")
      .then((response) => {
        setCoffees(response.data.data);
      })
      .catch((error) => {
        console.log(error);
      });
      setLoader(false)
  };

  return (
    <>
      {loader ? (
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "100%"
          }}
        >
          <CircularProgress style={{scale:"2"}}/>
        </div>
      ) : (
        <Grid container spacing={2} style={{ paddingTop: 48 }}>
          <Grid item xs={10}>
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                flexWrap: "wrap",
                justifyContent: "center",
              }}
            >
              {coffees &&
                coffees.map((coffee, index) => (
                  <Coffee coffee={coffee} key={index} />
                ))}
            </div>
          </Grid>
          <Grid item xs={2}>
            <Receipt />
          </Grid>
        </Grid>
      )}
    </>
  );
}

export default Home;
