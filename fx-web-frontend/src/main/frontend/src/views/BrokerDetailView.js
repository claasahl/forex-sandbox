import React from "react";
import { graphql } from "react-apollo";
import BrokersQuery from "../queries/BrokersQuery";

class BrokerDetailView extends React.Component {
  render() {
    const { data } = this.props;
    if (data.loading) {
      return <div>Loading ...</div>;
    }
    const { brokers } = data;
    const broker = brokers[0] || {};
    return (
      <div>
        <h1>{broker.name}</h1>
        <ul>
          {broker.symbols.map(symbol => {
            return (
              <li key={symbol.id}>
                {symbol.name}
                {symbol.duration && ` - ${symbol.duration}`}
              </li>
            );
          })}
        </ul>
      </div>
    );
  }
}

const queryOptions = {
  options: ({ match }) => ({
    variables: {
      brokerId: match.params.id,
    },
  }),
};

export default graphql(BrokersQuery, queryOptions)(BrokerDetailView);
