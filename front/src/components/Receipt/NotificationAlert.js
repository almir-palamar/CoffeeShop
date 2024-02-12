import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";

function NotificationAlert({ show, severity, message, onClose }) {
  return (
    <>
      <Snackbar
        open={show}
        autoHideDuration={5000}
        onClose={() => onClose()}
        anchorOrigin={{ horizontal: "left", vertical: "bottom" }}
      >
        <Alert
          severity={severity}
          sx={{ width: "100%" }}
          onClose={() => onClose()}
          variant="filled"
        >
          {message}
        </Alert>
      </Snackbar>
    </>
  );
}

export default NotificationAlert;