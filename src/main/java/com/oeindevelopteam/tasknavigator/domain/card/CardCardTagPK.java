package com.oeindevelopteam.tasknavigator.domain.card;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CardCardTagPK implements Serializable {

  private Long card;
  private Long tag;
}
