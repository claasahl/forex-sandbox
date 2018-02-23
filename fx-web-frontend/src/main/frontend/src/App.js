import React from "react";
import { ApolloProvider } from "react-apollo";
import { ApolloClient } from "apollo-client";
import { HttpLink } from "apollo-link-http";
import { InMemoryCache } from "apollo-cache-inmemory";
import Routing from "./views/Routing";
import "./App.css";

const link = new HttpLink({
  uri: "http://localhost:8080/fx/graphql",
});
const cache = new InMemoryCache();
const client = new ApolloClient({
  link,
  cache,
});

class App extends React.Component {
  render() {
    return (
      <ApolloProvider client={client}>
        <Routing />
      </ApolloProvider>
    );
  }
}

export default App;
