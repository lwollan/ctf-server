import React, { Component } from 'react';
import ScoreBoard from './ScoreBoard';
import Datas from './Datas';
import Game from './Game';

export default class ScoreBoardApp extends Component {
    componentDidMount() {
        Datas.pollBoard();
    }

    render() {
        return (
            <main className="container">
                <Game />
                <ScoreBoard/>
            </main>
        );
    }
}
