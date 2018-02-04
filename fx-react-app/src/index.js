import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import {
    Environment,
    Network,
    RecordSource,
    Store,
  } from 'relay-runtime';
import {
    graphql,
    QueryRenderer
} from 'react-relay';
  
  function fetchQuery(
    operation,
    variables,
  ) {
    return fetch('http://localhost:8080/graphql', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        query: operation.text,
        variables,
      }),
    }).then(response => {
      return response.json();
    });
  }
  
  const environment = new Environment({
    network: Network.create(fetchQuery),
    store: new Store(new RecordSource()),  
  });

function Square(props) {
    return (
        <button className={props.winner ? 'square square-winner' : 'square'} onClick={props.onClick}>
            {props.value}
        </button>
    );
}

class Board extends React.Component {
    renderSquare(i) {
        return (<Square
            key={i}
            value={this.props.squares[i]}
            winner={this.props.winners.includes(i)}
            onClick={() => this.props.onClick(i)}
        />
        );
    }

    render() {
        let rows = [];
        for(let row = 0; row < this.props.rows; row++) {
            let cols = [];
            for(let col = 0; col < this.props.cols; col++) {
                cols.push([this.renderSquare(row*this.props.cols + col)]);
            }
            rows.push([<div key={row} className="board-row">{cols}</div>]);
        }
        return (
            <div>{rows}</div>
        );
    }
}

class Game extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            history: [{
                squares: Array(9).fill(null),
                changedPosition: null,
            }],
            stepNumber: 0,
            xIsNext: true,
            sortMovesAsc: true,
        };
    }

    handleClick(i) {
        const history = this.state.history.slice(0, this.state.stepNumber + 1);
        const current = history[history.length - 1];
        const squares = current.squares.slice();
        if (this.calculateWinner(squares) || squares[i]) {
            return;
        }
        squares[i] = this.state.xIsNext ? "X" : "O";
        this.setState({
            history: history.concat([{
                squares: squares,
                changedPosition: i,
            }]),
            stepNumber: history.length,
            xIsNext: !this.state.xIsNext,
            sortMovesAsc: this.state.sortMovesAsc,
        });
    }

    calculateWinner(squares) {
        const lines = [
            [0, 1, 2],
            [3, 4, 5],
            [6, 7, 8],
            [0, 3, 6],
            [1, 4, 7],
            [2, 5, 8],
            [0, 4, 8],
            [2, 4, 6]
        ];
        for (let i = 0; i < lines.length; i++) {
            const [a, b, c] = lines[i];
            if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
                return {
                    player: squares[a],
                    line: lines[i].slice(),
                }
            }
        }
        return null;
    }

    jumpTo(move) {
        this.setState({
            stepNumber: move,
            xIsNext: (move % 2) === 0,
        });
    }

    sortMoves(event) {
        this.setState({
            sortMovesAsc: !this.state.sortMovesAsc,
        });
    }

    render() {
        const history = this.state.history;
        const current = history[this.state.stepNumber];
        const winner = this.calculateWinner(current.squares);

        const moves = history.map((step, move) => {
            const currentStep = this.state.stepNumber === move;
            const desc = move ? 
                "Go to move #" + move + " (" + step.changedPosition % 3 + "," + Math.floor(step.changedPosition / 3) + ")":
                "Go to game start";
            return (
                <li key={move}>
                    <button onClick={() => this.jumpTo(move)}>{currentStep ? <b>{desc}</b> : desc}</button>
                </li>
            );
        });
        let status;
        let wonLine;
        if (winner) {
            status = "Winner: " + winner.player;
            wonLine = winner.line.slice();
        } else {
            status = 'Next player: ' + (this.state.xIsNext ? "X" : "O");
            wonLine = [];
        }

        const sortBy = this.state.sortMovesAsc ? 
            "Sort history (DESC)" :
            "Sort history (ASC)";
        const sortedMoves = this.state.sortMovesAsc ? moves : moves.slice().reverse();

        return (
            <div className="game">
                <div className="game-board">
                    <Board
                        cols={3}
                        rows={3}
                        squares={current.squares}
                        winners={wonLine}
                        onClick={(i) => this.handleClick(i)}
                    />
                </div>
                <div className="game-info">
                    <div>{status}</div>
                    <div><button onClick={() => this.sortMoves()}>{sortBy}</button></div>
                    <ol>{sortedMoves}</ol>
                </div>
            </div>
        );
    }
}

class TestReactRelayApp extends React.Component {
    render() {
        return (
            <QueryRenderer
              environment={environment}
              query={graphql`
              query srcAllLinksQuery {
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
              `}
              variables={{}}
              render={({error, props}) => {
                if (error) {
                  return <div>Error!{error.message}</div>;
                }
                if (!props) {
                  return <div>Loading...</div>;
                }
                return <div>User ID: {props.viewer.id}</div>;
              }}
            />
          );
    }
}

// ========================================

ReactDOM.render(
    <div>
        <TestReactRelayApp />
    </div>,
    document.getElementById('root')
);
