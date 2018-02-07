import React from 'react';
import './App.css';
import { ApolloClient } from 'apollo-client';
import { ApolloProvider } from 'react-apollo';
import { HttpLink } from 'apollo-link-http';
import { InMemoryCache } from 'apollo-cache-inmemory';
import AllLinksView from './AllLinksView';
import HomeView from './HomeView';
import CreateLinkView from './CreateLinkView';
import { BrowserRouter, Route, Link, Router } from 'react-router-dom'

const client = new ApolloClient({
  link: new HttpLink({ uri: 'http://localhost:8080/graphql' }),
  cache: new InMemoryCache()
});

class App extends React.Component {
  render() {
    return (
      <ApolloProvider client={client}>
        <BrowserRouter>
          <div>
            <nav>
              <Link to="/">Home</Link>
              <Link to="/allLinks">All Links</Link>
              <Link to="/createLink">Create Link</Link>
            </nav>
            <Route exact path="/" component={HomeView}/>
            <Route exact path="/allLinks" component={AllLinksView}/>
            <Route exact path="/createLink" component={CreateLinkView}/>
          </div>
        </BrowserRouter>
      </ApolloProvider>
    );
  }
}

export default App;
