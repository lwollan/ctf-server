
import React from 'react';
import Client from './Client';

const ScoreBoard = React.createClass({
  getInitialState: function () {
    return {
      scores: [],
      title: 'Venter på poengsummer'
    };
  },
  handleSearchChange: function (e) {
    const value = e.target.value;

    this.setState({
      searchValue: value,
    });

    if (value === '') {
      this.setState({
        scores: [],
        title: 'Venter på poengsummer'
      });
    } else {
        this.setState({});

        Client.search(response => {
            this.setState({
                scores: response.score.slice(0),
                title: response.title
            });
        });
    }
  },
  render: function () {
    return (
      <div>
      { this.state.title }
        <table>
          <thead>
            <tr>
              <th>Team</th>
              <th>Score</th>
            </tr>
          </thead>
          <tbody>
          {
            this.state.scores.map((score, idx) => (
              <tr key={idx}>
                <td>{score.team}</td>
                <td>{score.score}</td>
              </tr>
            ))
          }
        </tbody>
        </table>
            <div>
                <input type='submit' value="Reload" onClick={this.handleSearchChange}/>
            </div>
      </div>
    );
  },
});

export default ScoreBoard;
