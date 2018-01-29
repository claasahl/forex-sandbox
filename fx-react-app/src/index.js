import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';


function Square(props) {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}

class Board extends React.Component {
    renderSquare(i) {
        return (<Square
            key={i}
            value={this.props.squares[i]}
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
                return squares[a];
            }
        }
        return null;
    }

    jumpTo(move) {
        const step = this.state.history[move];
        this.setState({
            stepNumber: move,
            xIsNext: (move % 2) === 0,
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
        if (winner) {
            status = "Winner: " + winner;
        } else {
            status = 'Next player: ' + (this.state.xIsNext ? "X" : "O");
        }
        return (
            <div className="game">
                <div className="game-board">
                    <Board
                        cols={3}
                        rows={3}
                        squares={current.squares}
                        onClick={(i) => this.handleClick(i)}
                    />
                </div>
                <div className="game-info">
                    <div>{status}</div>
                    <ol>{moves}</ol>
                </div>
            </div>
        );
    }
}

// ========================================

ReactDOM.render(
    <Game />,
    document.getElementById('root')
);
