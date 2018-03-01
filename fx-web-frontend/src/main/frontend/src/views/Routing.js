import React from "react";
import { BrowserRouter as Router, Link, Route } from "react-router-dom";
import HomeView from "./HomeView";
import BrokersView from "./BrokersView";
import BrokerDetailView from "./BrokerDetailView";
import ApiExplorer from "./ApiExplorer";

export default class Routing extends React.Component {
  render() {
    return (
      <Router>
        <div>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/brokers">Brokers</Link>
            </li>
            <li>
              <Link to="/api/explorer">API Explorer</Link>
            </li>
          </ul>
          <Route exact path="/" component={HomeView} />
          <Route exact path="/brokers" component={BrokersView} />
          <Route exact path="/brokers/:id" component={BrokerDetailView} />
          <Route exact path="/api/explorer" component={ApiExplorer} />
        </div>
      </Router>
    );
  }
}
