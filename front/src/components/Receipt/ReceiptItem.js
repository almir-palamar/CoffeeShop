import DeleteIcon from "@mui/icons-material/Delete";
import IconButton from "@mui/material/IconButton";
import ListItem from "@mui/material/ListItem";
import Chip from "@mui/material/Chip";
import { useDispatch } from "react-redux";
import { removeItem } from "../../store/OrderSlice";
import { Typography } from "@mui/material";
import { memo } from "react";

function ReceiptItem({ item }) {
  const dispatch = useDispatch();
  return (
    <>
      <ListItem
        sx={{
          minHeight: 40,
          justifyContent: "space-between",
          paddingTop: 0,
          paddingBottom: 0,
        }}
      >
        <div style={{display: 'flex'}}>
          <Typography>{item.type}</Typography>
          <Chip label={item.count} color="primary" size="small" style={{marginLeft: 5}} />
        </div>

        <IconButton onClick={() => dispatch(removeItem(item))}>
          <DeleteIcon style={{ color: "black" }} />
        </IconButton>
      </ListItem>
    </>
  );
}

export default memo(ReceiptItem);
