import React from "react";
import "graphiql/graphiql.css";
import GraphiQL from "graphiql";

export default class CustomGraphiQL extends React.Component {
  graphQLFetcher(graphQLParams) {
    return fetch(this.props.fetchURL, {
      method: "post",
      body: JSON.stringify(graphQLParams),
      credentials: "include",
    })
      .then(function(response) {
        return response.text();
      })
      .then(function(responseBody) {
        try {
          return JSON.parse(responseBody);
        } catch (error) {
          return responseBody;
        }
      });
  }

  render() {
    return <GraphiQL fetcher={this.graphQLFetcher.bind(this)} />;
  }
}
