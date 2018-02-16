import React from "react";
import "graphiql/graphiql.css";
import "./App.css";
import Graphiql from "graphiql";

class App extends React.Component {
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
    return <Graphiql fetcher={this.graphQLFetcher.bind(this)} />;
  }
}

export default App;
