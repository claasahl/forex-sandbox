import React from 'react';
import { graphql } from 'react-apollo';
import gql from 'graphql-tag';

const query = gql`
query AllLinksViewQuery {
  allLinks {
    id
    url
    description
    postedBy {
      id
      name
    }
  }
}
`;

class AllLinksView extends React.Component {
  render() {
    let {data} = this.props;
    if(data.loading) {
      return <div>Loading ...</div>
    }
    let {allLinks, refetch} = data;
    return (
      <div>
        <button onClick={() => refetch()}>
          Refresh
        </button>
        <ul>
          {allLinks && allLinks.map(link => (
            <li key={link.id}>
              <a href={link.url}>{link.description}</a>
            </li>
          ))}
        </ul>
      </div>
    );
  }
}

AllLinksView = graphql(query)(AllLinksView)
export default AllLinksView;