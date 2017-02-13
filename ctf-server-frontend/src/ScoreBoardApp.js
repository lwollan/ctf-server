import React, { Component } from 'react';
import ScoreBoard from './ScoreBoard';
import Datas from './Datas';
import Game from './Game';

export default class ScoreBoardApp extends Component {
    state = {};

    componentDidMount() {
        Datas.subscribe(this.unsub = state => this.setState({ board: state.get('board') }));

        Datas.pollBoard();
    }

    componentWillUnmount() {
        Datas.unsubscribe(this.unsub);
    }

    render() {
        const { board } = this.state;

        return (
            <main className="container">
                <div className="block">
                    <h1>{ board && board.get('title') }</h1>
                </div>
                { location.href.indexOf('show-board') === -1 ? <Game /> : <ScoreBoard/> }
            </main>
        );
    }
}
