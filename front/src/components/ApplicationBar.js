import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import { Outlet, Link } from "react-router-dom";
import LocalCafeIcon from "@mui/icons-material/LocalCafe";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";

function ApplicationBar() {
  return (
    <>
      <nav>
        <AppBar style={{ zIndex: 1500 }}>
          <Toolbar variant="dense" style={{ justifyContent: "space-between" }}>
            <Button style={{ color: "white" }} startIcon={<LocalCafeIcon />}>
              <Link to="/" style={{ textDecoration: "none", color: "inherit" }}>
                <Typography style={{ textTransform: "none" }}>
                  <b>CoffeeShop</b>
                </Typography>
              </Link>
            </Button>
          </Toolbar>
        </AppBar>
      </nav>
      <Outlet />
    </>
  );
}

export default ApplicationBar;
