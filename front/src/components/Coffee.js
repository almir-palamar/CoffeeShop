import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import EuroSymbolIcon from "@mui/icons-material/EuroSymbol";
import Chip from "@mui/material/Chip";
import AddIcon from '@mui/icons-material/Add';
import LocalCafeIcon from '@mui/icons-material/LocalCafe';
import { useDispatch } from "react-redux";
import { addItem } from "../store/OrderSlice";

function Coffee({coffee}) {
  const dispatch = useDispatch();
  return (
    <>
      <Card
        elevation={10}
        style={{ width: "230px", margin: "10px", height: "fit-content", transition: "transform 0.3s" }}
      >
        <CardMedia
          sx={{
            height: 0,
            paddingTop: "100%",
            position: "relative",
            overflow: "hidden",
          }}
          image={`${process.env.REACT_APP_BACKEND_URL}/storage/${coffee.image}`}
          title="coffee"
        >
          <CardContent
            style={{
              position: "absolute",
              bottom: 0,
              width: "100%",
              backgroundColor: "rgba(0, 0, 0, 0.5)",
              color: "white",
              padding: "10px", 
              transition: "opacity 0.3s",
            }}
          >
            <Typography variant="h6" component="div">
              {coffee.type}
            </Typography>
          </CardContent>
        </CardMedia>
        <CardContent style={{ padding: "8px" }}>
          <div style={{display: "flex", justifyContent: "center"}}>
            <Chip
              label={<b style={{color: "gray"}}>{coffee.brew_time}"</b>}
              avatar={
                <AccessTimeIcon
                  style={{
                    color: "gra",
                    backgroundColor: "transparent",
                  }}
                />
              }
              variant="fullfiled"
              style={{backgroundColor: "transparent"}}
            />
            <Chip
              label={<b style={{color: "gray"}}>{coffee.price}</b>}
              avatar={
                <EuroSymbolIcon
                  style={{
                    color: "gray",
                    backgroundColor: "transparent",
                  }}
                />
              }
              variant="fullfiled"
              style={{backgroundColor: "transparent"}}
            />
            <Chip
              label={<b style={{color: "gray"}}>{coffee.coffee_amount}g</b>}
              avatar={
                <LocalCafeIcon
                  style={{
                    color: "gray",
                    backgroundColor: "transparent",
                  }}
                />
              }
              variant="fullfiled"
              style={{backgroundColor: "transparent"}}
            />
          </div>
        </CardContent>
        <CardActions style={{padding: 0}}>
          <Button
            variant="contained"
            style={{ width: "100%", borderRadius: 0, minHeight: 48 }}
            startIcon={<AddIcon />}
            color="primary"
            size="large"
            disableElevation
            onClick={() => dispatch(addItem(coffee))}
          >
            <Typography style={{ textTransform: "none" }}>Add</Typography>
          </Button>
        </CardActions>
      </Card>
    </>
  );
}

export default Coffee;
