import React from "react";
import CustomGraphiQL from "./views/CustomGraphiQL";
import "./App.css";

class App extends React.Component {
  render() {
    return <CustomGraphiQL fetchURL="http://localhost:8080/fx/graphql" />;
  }
}

export default App;
