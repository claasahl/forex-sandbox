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
    let {allLinks} = data;
    return (
      <div>
        <ul>
          {allLinks && allLinks.map(link => (
            <a key={link.id} href={link.url}>
              {link.description}
            </a>
          ))}
        </ul>
      </div>
    );
  }
}

AllLinksView = graphql(query)(AllLinksView)
export default AllLinksView;