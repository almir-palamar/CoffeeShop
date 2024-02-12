import "./App.css";
import ApplicationBar from "./components/ApplicationBar";
import Home from "./pages/Home";
import NoPage from "./pages/NoPage";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import store from "./store";
import { Provider } from "react-redux";

function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<ApplicationBar />}>
            <Route path="/" index element={<Home />} />
            <Route path="*" element={<NoPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </Provider>
  );
}

export default App;
