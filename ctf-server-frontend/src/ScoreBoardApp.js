import React, { Component } from 'react';
import ScoreBoard from './ScoreBoard';
import Datas from './Datas';
import Game from './Game';

const showBoard  = location.href.indexOf('show-board') !== -1;

export default class ScoreBoardApp extends Component {
    state = {};

    componentDidMount() {
        Datas.subscribe(this.unsub = state => this.setState({ board: state.get('board') }));

        showBoard ? Datas.pollBoard() : Datas.getBoard();
    }

    componentWillUnmount() {
        Datas.unsubscribe(this.unsub);
    }

    render() {
        const { board } = this.state;

        return (
            <main className="container">
                <div className="block">
                    <h1>{ board && board.get('title') } </h1>
                    <p> { board && board.get('beskrivelse') } </p>
                </div>
                { showBoard ? <ScoreBoard/> : <Game /> }
            </main>
        );
    }
}
