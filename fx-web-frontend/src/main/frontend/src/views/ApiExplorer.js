import React from "react";
import CustomGraphiQL from "./CustomGraphiQL";

export default class ApiExplorer extends React.Component {
  render() {
    return <CustomGraphiQL fetchURL="http://localhost:8080/fx/graphql" />;
  }
}
