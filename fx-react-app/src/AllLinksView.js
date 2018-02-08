import React from 'react';
import { Link } from 'react-router-dom';
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
              <Link to={`/links/${link.id}`}>{link.description}</Link>
            </li>
          ))}
        </ul>
      </div>
    );
  }
}

AllLinksView = graphql(query)(AllLinksView)
export default AllLinksView;