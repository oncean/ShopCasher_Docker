package com.wangsheng.baseDao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class Condition {

	private static Specification toSpecification(List<Predicate> predicates) {
		return new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};
	}
}