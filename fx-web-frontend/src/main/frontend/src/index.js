import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import registerServiceWorker from "./registerServiceWorker";

ReactDOM.render(
  <App fetchURL="http://localhost:8080/fx/graphql" />,
  document.getElementById("root")
);
registerServiceWorker();
