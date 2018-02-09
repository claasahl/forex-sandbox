import React from 'react';
import './App.css';
import { ApolloClient } from 'apollo-client';
import { ApolloProvider } from 'react-apollo';
import { HttpLink } from 'apollo-link-http';
import { toIdValue } from 'apollo-utilities';
import { InMemoryCache } from 'apollo-cache-inmemory';
import AllLinksView from './AllLinksView';
import HomeView from './HomeView';
import CreateLinkView from './CreateLinkView';
import DetailLinkView from './DetailLinkView';
import { BrowserRouter, Route, Link, Router } from 'react-router-dom'

const cache = new InMemoryCache({
  cacheResolvers: {
    Query: {
      oneLink: (_, args) => toIdValue(cache.config.dataIdFromObject({ __typename: 'Link', id: args.id })),
    }
  }
});
const client = new ApolloClient({
  link: new HttpLink({ uri: 'http://localhost:8080/graphql' }),
  cache
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
            <Route path="/links/:id" component={DetailLinkView}/>
          </div>
        </BrowserRouter>
      </ApolloProvider>
    );
  }
}

export default App;
