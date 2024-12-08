import Grid from "@mui/material/Grid";
import CircularProgress from "@mui/material/CircularProgress";
import Coffee from "../components/Coffee";
import Receipt from "../components/Receipt/Receipt";
import useGetCoffees from "../hooks/useGetCoffees";

function Home() {

  const { loader, coffees } = useGetCoffees();

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
