import React, { Component } from 'react';
import Datas from './Datas';

export default class ScoreBoard extends Component {

    state = {};

    componentDidMount() {
        Datas.subscribe(this.unsub = state => this.setState({ board: state.get('board') }));
    }

    componentWillUnmount() {
        Datas.unsubscribe(this.unsub);
    }

    render() {
        const { board } = this.state;

        if (!board) {
            return (
                <div>Loading board</div>
            );
        }

        return (
            <div className="block">
                { board.get('title') }
                <table className="scoreboard" width="100%">
                    <thead>
                    <tr>
                        <th>Team</th>
                        <th>Score</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        board && board.get('score')
                            .sort((a, b) => a.get('score') > b.get('score') ? -1 : 1)
                            .map(score => (
                                <tr key={ score.get('team') }>
                                    <td>{ score.get('team') }</td>
                                    <td>{ score.get('score') }</td>
                                </tr>
                            ))
                    }
                    </tbody>
                </table>
            </div>
        );
    }
}