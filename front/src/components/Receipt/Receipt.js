import Drawer from "@mui/material/Drawer";
import Toolbar from "@mui/material/Toolbar";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Divider from "@mui/material/Divider";
import CircularProgress from "@mui/material/CircularProgress";
import ReceiptItem from "./ReceiptItem";
import GradingIcon from "@mui/icons-material/Grading";
import ReorderIcon from "@mui/icons-material/Reorder";
import NotificationAlert from "./NotificationAlert";
import useReceiptLogic from "../../hooks/useReceipt";

function Receipt() {
  const {
    orderItems,
    total,
    btnLoading,
    orderReady,
    orderAccepted,
    orderNo,
    makeOrder,
    setOrderReady,
    setOrderAccepted
  } = useReceiptLogic();

  return (
    <>
      <NotificationAlert
        show={orderReady}
        severity={"success"}
        message={`Your order number ${orderNo} is ready!`}
        onClose={() => setOrderReady(false)}
      />
      <NotificationAlert
        show={orderAccepted}
        severity={"info"}
        message={`Your order is confirmed! Order number: ${orderNo}`}
        onClose={() => setOrderAccepted(false)}
      />

      <Drawer open={true} anchor="right" variant="permanent">
        <Toolbar
          variant="dense"
          style={{
            backgroundColor: "#2e7d32",
            color: "white",
            paddingTop: 48,
            paddingLeft: 16,
          }}
        >
          <ReorderIcon />
          <Typography style={{ paddingLeft: 8 }}>Order overview</Typography>
        </Toolbar>
        <List
          disablePadding
          style={{ overflowY: "auto", height: "100%", width: 300 }}
        >
          {orderItems.map((item, index) => (
            <ReceiptItem item={item} key={index} />
          ))}
        </List>
        <Divider />
        <ListItem disablePadding style={{ minHeight: 48 }}>
          <Typography style={{ textTransform: "none", paddingLeft: 8, justifyContent: "space-between", display: "flex", width: "100%",paddingRight: 8 }}>
            <b>Total: </b>
            <b>{total} €</b>
          </Typography>
        </ListItem>
        <ListItem
          disablePadding
          style={{ paddingBottom: "0px", minHeight: 48 }}
        >
          <Button
            startIcon={!btnLoading && <GradingIcon />}
            variant="contained"
            style={{ borderRadius: 0, width: "inherit", height: "100%" }}
            color="success"
            size="large"
            disableElevation
            disabled={orderItems.length === 0 || btnLoading}
            onClick={makeOrder}
          >
            {!btnLoading ? (
              <Typography style={{ textTransform: "none" }}>
                Make order
              </Typography>
            ) : (
              <CircularProgress
                color="success"
                size={24}
                style={{ padding: "0", margin: "0" }}
              />
            )}
          </Button>
        </ListItem>
      </Drawer>
    </>
  );
}

export default Receipt;
