import React from "react";
import { graphql } from "react-apollo";
import BrokersQuery from "../queries/BrokersQuery";

class BrokersView extends React.Component {
  render() {
    const { data } = this.props;
    if (data.loading) {
      return <div>Loading ...</div>;
    }
    const { brokers } = data;
    let symbols = [];
    brokers.forEach(broker => {
      broker.symbols.forEach(symbol => {
        const symbolId = broker.id + "-" + symbol.name + "-" + symbol.duration;
        symbols.push({
          ...symbol,
          brokerId: broker.id,
          brokerName: broker.name,
          id: symbolId,
        });
      });
    });
    return (
      <ul>
        {symbols.map(symbol => {
          return (
            <li key={symbol.id}>
              {symbol.brokerName} - {symbol.name} - {symbol.duration}
            </li>
          );
        })}
      </ul>
    );
  }
}

export default graphql(BrokersQuery)(BrokersView);
