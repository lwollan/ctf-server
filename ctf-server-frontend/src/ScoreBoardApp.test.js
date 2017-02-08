import React from 'react';
import ReactDOM from 'react-dom';
import ScoreBoardApp from './ScoreBoardApp';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<ScoreBoardApp />, div);
});
