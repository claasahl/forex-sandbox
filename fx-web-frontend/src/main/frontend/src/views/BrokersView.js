import React from "react";
import { Link } from "react-router-dom";
import { graphql } from "react-apollo";
import BrokersQuery from "../queries/BrokersQuery";

class BrokersView extends React.Component {
  render() {
    const { data } = this.props;
    if (data.loading) {
      return <div>Loading ...</div>;
    }
    const { brokers } = data;
    return (
      <ul>
        {brokers.map(broker => {
          return (
            <li key={broker.id}>
              <Link to={`/brokers/${broker.id}`}>{broker.name}</Link>
            </li>
          );
        })}
      </ul>
    );
  }
}

export default graphql(BrokersQuery)(BrokersView);
