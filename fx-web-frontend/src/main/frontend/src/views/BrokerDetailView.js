import React from "react";
import { graphql } from "react-apollo";
import BrokerQuery from "../queries/BrokerQuery";

class BrokerDetailView extends React.Component {
  render() {
    const { data: { loading, broker } } = this.props;
    if (loading) {
      return <div>Loading ...</div>;
    }
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

export default graphql(BrokerQuery, queryOptions)(BrokerDetailView);
