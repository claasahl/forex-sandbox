import React from "react";
import { Link } from "react-router-dom";
import { graphql } from "react-apollo";
import BrokersQuery from "../queries/BrokersQuery";

class BrokersView extends React.Component {
  render() {
    const { data: { loading, brokers } } = this.props;
    if (loading) {
      return <div>Loading ...</div>;
    }
    return (
      <div>
        <h1>Brokers</h1>
        <ul>
          {brokers.map(broker => {
            return (
              <li key={broker.id}>
                <Link to={`/brokers/${broker.id}`}>{broker.name}</Link>
              </li>
            );
          })}
        </ul>
      </div>
    );
  }
}

export default graphql(BrokersQuery)(BrokersView);
